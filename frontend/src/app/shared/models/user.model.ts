import { UserType } from "./user-type.model";

export interface User {
    id?: number;
    name:string;
    firstname:string;
    email:string;
    userType:UserType;
}