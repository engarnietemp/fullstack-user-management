export interface UserRequest {
  name?: string;
  firstname?: string;
  email?: string;
  userType?: {
    id?: number;
  };
}