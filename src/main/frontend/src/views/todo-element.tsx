import {Checkbox} from "@/components/ui/checkbox.tsx";
import type {Todo} from "@/api/model";

interface TodoElementProps {
    todo: Todo;
}

export default function TodoElement({ todo }: TodoElementProps) {
    return (
        <div className={`p-3 border rounded-xl shadow-sm flex flex-row ${todo.completedAt && "bg-slate-100"}`}>
            <div className={"flex items-center mr-4"}>
                <Checkbox />
            </div>
            <div>
                <h3 className="text-base">{todo.title}</h3>
                <p className="text-sm text-gray-500">Created by: {todo.createdBy}</p>
            </div>
        </div>
    );

}