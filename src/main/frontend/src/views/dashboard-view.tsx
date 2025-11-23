import {useEffect, useState} from "react";
import { useAuth } from "../auth/AuthContext.tsx";
import {Button} from "@/components/ui/button.tsx";

export default function DashboardView() {
    const [message, setMessage] = useState<string>("");
    const { user } = useAuth();

    useEffect(() => {
        fetch("/api/hello")
            .then(res => res.text())
            .then(body => setMessage(body))
            .catch(err => setMessage("Error fetching message: " + err.message));
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
            <div>Message: {message}</div>

            <Button variant={"outline"} onClick={() => window.location.href = "/logout"}>
                Sign Out
            </Button>
        </>

    );
}
