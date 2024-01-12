import useAuth from "./useAuth";
import axios from "../axiosConfiguration";
import { removeEmailID } from "./useEmail";
const useLogout = () => {
    const { setAuth } = useAuth();

    const logout = async () => {
        setAuth({});
        removeEmailID();
        try {
            await axios('/unauthenticated/logout', {
                withCredentials: true
            });
        } catch (err) {
            console.error(err);
        }
    }

    return logout;
}

export default useLogout