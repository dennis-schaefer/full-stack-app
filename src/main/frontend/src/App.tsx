import {
    createBrowserRouter,
    RouterProvider,
} from "react-router-dom";
import DashboardView from "./views/dashboard-view.tsx";
import PublicView from "./views/public-view.tsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <DashboardView/>,
    },
    {
        path: "/public",
        element: <PublicView />
    }
]);

export default function App() {
    return (
            <RouterProvider router={router}/>
    );
}