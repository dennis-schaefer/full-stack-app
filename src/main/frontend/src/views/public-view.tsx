import { useAuth } from "../auth/AuthContext.tsx";
import {Button} from "@/components/ui/button.tsx";
import {NavLink} from "react-router-dom";

export default function PublicView() {
    const { user, isAuthenticated, login } = useAuth();

    return (
        <div>
            <h1>Public View</h1>
            <p>This is a publicly available view</p>

            {isAuthenticated ? (
                <div>
                    <p>You are authenticated as: <strong>{user?.fullName}</strong></p>
                    <NavLink to={"/"}>
                        <Button variant={"link"}>Go to dashboard</Button>
                    </NavLink>
                </div>
            ) : (
                <div>
                    <p>You are currently not logged in</p>
                    <Button onClick={login}>Sign in here</Button>
                </div>
            )}
        </div>
    );
}