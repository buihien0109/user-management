import React from "react";
import { Link } from "react-router-dom";

function NotFound() {
    return (
        <div className="container">
            <h2>Page Not Found</h2>
            <Link to={"/users"} className="btn btn-primary">
                Go to Home Page
            </Link>
        </div>
    );
}

export default NotFound;
