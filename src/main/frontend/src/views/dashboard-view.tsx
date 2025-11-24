import {useEffect, useState} from "react";
import { useAuth } from "../auth/AuthContext.tsx";
import {Button} from "@/components/ui/button.tsx";
import TodoElement from "@/views/todo-element.tsx";
import {Configuration, type Todo, TodoControllerApi} from "@/commons";

const configuration = new Configuration({
    basePath: ''
});
const apiInstance = new TodoControllerApi(configuration);

export default function DashboardView() {
    const [todos, setTodos] = useState<Todo[]>([]);
    const { user } = useAuth();

    useEffect(() => {
        const fetchTodos = async () => {
            const { data } = await apiInstance.getTodos();
            return data;
        }
        fetchTodos().then(data => setTodos(data));
    }, [])

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

                { todos.map(todo => (
                    <TodoElement todo={todo} key={todo.id} />
                ))}
            </div>
        </>

    );
}
