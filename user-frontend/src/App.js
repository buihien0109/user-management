import { Navigate, Route, Routes } from "react-router-dom";
import { ToastContainer } from 'react-toastify';
import "./App.css";
import Header from "./components/Header";
import NotFound from "./components/NotFound";
import UserAdd from "./features/user/UserAdd";
import UserEdit from "./features/user/UserEdit";
import UserList from "./features/user/UserList";

function App() {
  return (
    <>
      <Header />

      <Routes>
        <Route
          path="/"
          element={<Navigate to="/users" replace />}
        />
        <Route path="/users">
          <Route index element={<UserList />} />
          <Route path="create" element={<UserAdd />} />
          <Route path=":userId" element={<UserEdit />} />
        </Route>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </>
  );
}

export default App;
