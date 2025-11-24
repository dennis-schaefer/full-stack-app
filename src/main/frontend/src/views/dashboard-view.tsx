import { useAuth } from "../auth/AuthContext.tsx";
import {Button} from "@/components/ui/button.tsx";
import TodoElement from "@/views/todo-element.tsx";
import {useGetTodos} from "@/api/endpoints/todo-controller/todo-controller.ts";

export default function DashboardView() {
    const { data: todos, isLoading } = useGetTodos();
    const { user } = useAuth();


    return (
        <>
            <div>Dashboard</div>
            {user && (
                <div>
                    <h2>Welcome, {user.fullName}!</h2>
                    <p>Username: {user.username}</p>
                    <p>E-Mail: {user.email}</p>
                    <p>Roles: {user.roles.flatMap(role => role + ", ")}</p>
                </div>
            )}


            <Button variant={"outline"} onClick={() => window.location.href = "/logout"}>
                Sign Out
            </Button>

            <div className={"p-4 gap-2 flex flex-col"}>
                <div className={"text-xl"}>ToDo's</div>

                { isLoading && <div>Loading...</div> }

                { todos && todos.map(todo => (
                    <TodoElement todo={todo} key={todo.id} />
                ))}
            </div>
        </>

    );
}
