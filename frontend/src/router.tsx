import { createRootRoute, createRoute } from '@tanstack/react-router';
import { CourseListPage } from './pages/CourseListPage';
import { LessonPage } from './pages/LessonPage';

const rootRoute = createRootRoute({
});

const indexRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: '/',
  component: CourseListPage,
});

const lessonRoute = createRoute({
  getParentRoute: () => rootRoute,
  path: '/lessons/$lessonId',
  component: LessonPage,
});

export const routeTree = rootRoute.addChildren([indexRoute, lessonRoute]);