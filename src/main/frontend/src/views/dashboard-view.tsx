import {useEffect, useState} from "react";
import { useAuth } from "../auth/AuthContext.tsx";
import {Button} from "@/components/ui/button.tsx";
import type Todo from "@/commons/Todo.ts";
import TodoElement from "@/views/todo-element.tsx";
import {Configuration, TodoControllerApi} from "@/commons";

const configuration = new Configuration();
const apiInstance = new TodoControllerApi(configuration);

export default function DashboardView() {
    const [todos, setTodos] = useState<Todo[]>([]);
    const { user } = useAuth();

    useEffect(() => {
        fetch("/api/v1/todos")
            .then(res => res.json())
            .then((data: Todo[]) => setTodos(data))
            .catch(err => console.error("Error fetching todos: " + err));

        apiInstance.getTodos().then;
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
