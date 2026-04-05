import { useEffect, useState } from 'react'

const API_BASE = import.meta.env.VITE_API_BASE || '/api'

/**
 * Very small demo UI to prove backend endpoints and lesson completion flow.
 *
 * This is intentionally simple and not a production-ready UI.
 */
export default function App() {
  const [courses, setCourses] = useState([])
  const [units, setUnits] = useState([])
  const [selectedCourseId, setSelectedCourseId] = useState(null)
  const [lesson, setLesson] = useState(null)
  const [completeResult, setCompleteResult] = useState(null)
  const [error, setError] = useState('')

  useEffect(() => {
    loadCourses()
  }, [])

  async function loadCourses() {
    try {
      setError('')
      const response = await fetch(`${API_BASE}/courses`)
      if (!response.ok) {
        throw new Error('Failed to load courses')
      }
      const data = await response.json()
      setCourses(data)

      // Auto-select first course so demo is clickable right after page load.
      if (data.length > 0) {
        await selectCourse(data[0].id)
      }
    } catch (e) {
      setError(e.message)
    }
  }

  async function selectCourse(courseId) {
    try {
      setError('')
      setSelectedCourseId(courseId)
      setLesson(null)
      setCompleteResult(null)

      const response = await fetch(`${API_BASE}/courses/${courseId}/units`)
      if (!response.ok) {
        throw new Error('Failed to load units')
      }
      const data = await response.json()
      setUnits(data)
    } catch (e) {
      setError(e.message)
    }
  }

  async function loadLesson(lessonId) {
    try {
      setError('')
      setCompleteResult(null)

      const response = await fetch(`${API_BASE}/lessons/${lessonId}`)
      if (!response.ok) {
        throw new Error('Failed to load lesson')
      }
      const data = await response.json()
      setLesson(data)
    } catch (e) {
      setError(e.message)
    }
  }

  async function completeLesson(lessonId) {
    try {
      setError('')

      // MVP simplification: fixed demo userId = 1.
      const response = await fetch(`${API_BASE}/lessons/${lessonId}/complete`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ userId: 1 })
      })

      if (!response.ok) {
        throw new Error('Failed to complete lesson')
      }

      const data = await response.json()
      setCompleteResult(data)
    } catch (e) {
      setError(e.message)
    }
  }

  return (
    <div className="page">
      <h1>LELA MVP</h1>
      <p className="subtitle">Language learning with gamification and real rewards</p>

      {error && <div className="error">Error: {error}</div>}

      <section className="panel">
        <h2>Courses</h2>
        <div className="row-list">
          {courses.map((course) => (
            <button
              key={course.id}
              onClick={() => selectCourse(course.id)}
              className={selectedCourseId === course.id ? 'active' : ''}
            >
              {course.title}
            </button>
          ))}
        </div>
      </section>

      <section className="panel">
        <h2>Units (for selected course)</h2>
        {units.length === 0 && <p>No units yet.</p>}
        <ul>
          {units.map((unit) => (
            <li key={unit.id}>
              #{unit.position} {unit.title}
            </li>
          ))}
        </ul>
      </section>

      <section className="panel">
        <h2>Lesson Demo</h2>
        <p>
          For quick demo we load lesson with id = 1.
          <button onClick={() => loadLesson(1)} className="inline-btn">Load lesson #1</button>
        </p>

        {lesson && (
          <div className="lesson-box">
            <h3>{lesson.title}</h3>
            <p><strong>Unit:</strong> {lesson.unitTitle}</p>
            <p><strong>Points reward:</strong> {lesson.pointsReward}</p>
            <p>{lesson.content}</p>
            <button onClick={() => completeLesson(lesson.id)}>Complete this lesson</button>
          </div>
        )}
      </section>

      {completeResult && (
        <section className="panel success">
          <h2>Completion Result</h2>
          <pre>{JSON.stringify(completeResult, null, 2)}</pre>
        </section>
      )}
    </div>
  )
}
