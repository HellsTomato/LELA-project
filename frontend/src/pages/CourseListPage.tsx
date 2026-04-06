import React, {useEffect, useState} from 'react';
import {apiService, Course, Lesson, Unit} from '../services/api';
import {useNavigate} from '@tanstack/react-router';
import {Spinner} from '../components/Spinner';

export const CourseListPage: React.FC = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [resettingProgress, setResettingProgress] = useState(false);
  const [selectedCourseId, setSelectedCourseId] = useState<number | null>(() => {
    const saved = localStorage.getItem('lastSelectedCourseId');
    return saved ? parseInt(saved, 10) : null;
  });

  const [units, setUnits] = useState<Unit[]>([]);
  const [unitsLoading, setUnitsLoading] = useState(false);
  const [lessonsByUnit, setLessonsByUnit] = useState<Record<number, Lesson[]>>({});
  const navigate = useNavigate();

  useEffect(() => {
    apiService.getCourses()
      .then(setCourses)
      .catch((err) => setError(err.message || 'Failed to load courses'))
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    if (selectedCourseId) {
      localStorage.setItem('lastSelectedCourseId', selectedCourseId.toString());
      setUnitsLoading(true);
      apiService.getUnits(selectedCourseId)
        .then(async (data) => {
          setUnits(data);
          const lessonsMap: Record<number, Lesson[]> = {};
          await Promise.all(data.map(async (u) => {
            lessonsMap[u.id] = await apiService.discoverDemoLessons(u.id);
          }));
          setLessonsByUnit(lessonsMap);
        })
        .catch(() => setError('Failed to load units'))
        .finally(() => setUnitsLoading(false));
    } else {
      setUnits([]);
    }
  }, [selectedCourseId]);

  const handleResetProgress = async () => {
    if (!window.confirm('Вы уверены? Это сбросит весь прогресс и награды.')) {
      return;
    }
    setResettingProgress(true);
    try {
      await apiService.resetUserProgress(1); // userId: 1 (Alice)
      setSelectedCourseId(null);
      setUnits([]);
      setLessonsByUnit({});
      alert('Прогресс сброшен!');
      const updatedCourses = await apiService.getCourses();
      setCourses(updatedCourses);
    } catch (err: any) {
      setError(err.message || 'Failed to reset progress');
    } finally {
      setResettingProgress(false);
    }
  };

  if (loading) return <Spinner />;
  if (error) return <div className="text-error">{error}</div>;

  return (
    <div className="container">
      <div className="page-header">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
          <div>
            <h1>Курсы языка</h1>
            <p>Выберите курс для старта.</p>
          </div>
          <button 
            className="btn btn-secondary" 
            onClick={handleResetProgress} 
            disabled={resettingProgress}
            style={{ fontSize: '0.875rem', padding: '0.5rem 1rem', whiteSpace: 'nowrap' }}
          >
            {resettingProgress ? 'Сброс...' : '🔄 Сбросить'}
          </button>
        </div>
      </div>

      <div>
        {courses.map(course => (
          <div
            key={course.id}
            className={`course-card ${selectedCourseId === course.id ? 'active' : ''}`}
            onClick={() => setSelectedCourseId(course.id)}
          >
            <h2>{course.title}</h2>
            <p>{course.description}</p>
          </div>
        ))}
      </div>

      {selectedCourseId && (
        <div className="unit-list">
          <h3>Юниты курса</h3>
          {unitsLoading ? <Spinner /> : units.map(unit => (
            <div key={unit.id} className="unit-card">
              <h4>{unit.title}</h4>
              <div style={{ marginTop: '1rem' }}>
                {lessonsByUnit[unit.id] && lessonsByUnit[unit.id].length > 0 ? (
                  lessonsByUnit[unit.id].map(lesson => (
                    <div
                      key={lesson.id}
                      className="lesson-item"
                      onClick={() => navigate({ to: '/lessons/$lessonId', params: { lessonId: lesson.id.toString() } })}
                    >
                      <div>
                        <strong>{lesson.title}</strong>
                        <div className="lesson-meta">{lesson.pointsReward} XP</div>
                      </div>
                      <button className="btn btn-secondary">Начать</button>
                    </div>
                  ))
                ) : (
                  <p className="lesson-meta">В этом юните пока нет доступных уроков.</p>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};