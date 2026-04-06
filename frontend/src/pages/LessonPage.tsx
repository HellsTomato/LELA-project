import React, { useEffect, useState } from 'react';
import { apiService, Lesson, CompleteLessonResponse } from '../services/api';
import { useParams, useNavigate } from '@tanstack/react-router';
import { Spinner } from '../components/Spinner';

export const LessonPage: React.FC = () => {
  const { lessonId } = useParams({ strict: false }) as any;
  const navigate = useNavigate();

  const [lesson, setLesson] = useState<Lesson | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  
  const [completing, setCompleting] = useState(false);
  const [result, setResult] = useState<CompleteLessonResponse | null>(null);

  useEffect(() => {
    if (!lessonId) return;
    apiService.getLesson(parseInt(lessonId, 10))
      .then(setLesson)
      .catch((err: any) => setError(err.message || 'Failed to load lesson'))
      .finally(() => setLoading(false));
  }, [lessonId]);

  const handleComplete = async () => {
    setCompleting(true);
    try {
      const res = await apiService.completeLesson(parseInt(lessonId, 10), 1);
      setResult(res);
    } catch (err: any) {
      setError(err.message || 'Completion failed');
    } finally {
      setCompleting(false);
    }
  };

  if (loading) return <Spinner />;
  if (error) return <div className="container text-error">{error}</div>;
  if (!lesson) return <div className="container">Lesson not found.</div>;

  return (
    <div className="container" style={{ maxWidth: '600px', marginTop: '30px' }}>
      <button className="back-link btn-secondary" onClick={() => navigate({ to: '/' })} style={{ background: 'none' }}>
        Назад к списку
      </button>

      <div className="lesson-container">
        <h1>{lesson.title}</h1>
        <div className="pts-badge" style={{ display: 'inline-block', marginBottom: '1rem' }}>
          +{lesson.pointsReward} XP
        </div>
        
        <p className="lesson-content">{lesson.content}</p>

        {!result ? (
          <button 
            className="btn" 
            onClick={handleComplete} 
            disabled={completing} 
            style={{ width: '100%', marginTop: '2rem' }}
          >
            {completing ? 'Завершение...' : 'Завершить урок'}
          </button>
        ) : (
          <div className="stats-grid">
            <div className="stat-box">
              <small>Заработано</small>
              <strong>+{result.pointsEarned} XP</strong>
            </div>
            <div className="stat-box">
              <small>Всего XP</small>
              <strong>{result.totalPoints}</strong>
            </div>
          </div>
        )}
      </div>

      {result?.rewardUnlocked && result.unlockedRewards && result.unlockedRewards.length > 0 && (
        <div className="modal-overlay">
          <div className="modal-content" style={{ animation: 'bounceIn 0.5s ease' }}>
            <div className="reward-icon">🎉</div>
            <h2>Новая награда!</h2>
            <p style={{ marginTop: '1rem', marginBottom: '2rem' }}>
              Вы разблокировали: <strong>{result.unlockedRewards.join(', ')}</strong>
            </p>
            <button className="btn" onClick={() => navigate({ to: '/' })}>
              Отлично! Вернуться к курсам
            </button>
          </div>
        </div>
      )}
    </div>
  );
};
