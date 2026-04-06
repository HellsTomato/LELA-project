import { RouterProvider, createRouter } from "@tanstack/react-router";
import { routeTree } from "./router";

const router = createRouter({ routeTree });

const App = () => (
  <div className="min-h-screen bg-gray-50 text-gray-900 font-sans">
    <RouterProvider router={router} />
  </div>
);

export default App;
