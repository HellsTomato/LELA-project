import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
});

export interface Course {
  id: number;
  title: string;
  description: string;
}

export interface Unit {
  id: number;
  title: string;
  position: number;
  courseId: number;
}

export interface Lesson {
  id: number;
  title: string;
  content: string;
  pointsReward: number;
  unitId: number;
  unitTitle: string;
  courseId: number;
}

export interface CompleteLessonResponse {
  message: string;
  userId: number;
  lessonId: number;
  pointsEarned: number;
  totalPoints: number;
  rewardUnlocked: boolean;
  unlockedRewards: string[]; // as returned by backend
}

class ApiService {
  private lessonCache: Map<number, Lesson> = new Map();
  private allLessonsDiscovered = false;

  async getCourses(): Promise<Course[]> {
    const res = await api.get<Course[]>('/courses');
    return res.data;
  }

  async getUnits(courseId: number): Promise<Unit[]> {
    const res = await api.get<Unit[]>(`/courses/${courseId}/units`);
    return res.data;
  }

  async getLesson(lessonId: number): Promise<Lesson> {
    if (this.lessonCache.has(lessonId)) {
      return this.lessonCache.get(lessonId)!;
    }
    
    const res = await api.get<Lesson>(`/lessons/${lessonId}`);
    this.lessonCache.set(lessonId, res.data);
    return res.data;
  }

  async completeLesson(lessonId: number, userId: number): Promise<CompleteLessonResponse> {
    const res = await api.post<CompleteLessonResponse>(`/lessons/${lessonId}/complete`, { userId });
    return res.data;
  }

  async discoverDemoLessons(unitId: number): Promise<Lesson[]> {
    if (this.allLessonsDiscovered) {
      return Array.from(this.lessonCache.values())
        .filter(lesson => lesson.unitId === unitId);
    }

    const lessons: Lesson[] = [];
    let consecutiveNotFound = 0;
    const MAX_CONSECUTIVE_404 = 3;
    let lessonId = 1;

    while (consecutiveNotFound < MAX_CONSECUTIVE_404) {
      try {
        const lesson = await this.getLesson(lessonId);
        if (lesson.unitId === unitId) {
          lessons.push(lesson);
        }
        consecutiveNotFound = 0;
        lessonId++;
      } catch (error: any) {
        if (error.response?.status === 404) {
          consecutiveNotFound++;
          lessonId++;
        } else {
          break;
        }
      }
    }

    this.allLessonsDiscovered = true;
    return lessons;
  }

  async resetUserProgress(userId: number): Promise<void> {
    await api.delete(`/users/${userId}/progress`);
    this.lessonCache.clear();
    this.allLessonsDiscovered = false;
  }
}

export const apiService = new ApiService();