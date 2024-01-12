export const getEmailID = () => {
    return localStorage.getItem("emailID");
}

export const setEmailID = (emailID) => {
    return localStorage.setItem("emailID", emailID);
}

export const removeEmailID = () => {
    return localStorage.removeItem("emailID");
}