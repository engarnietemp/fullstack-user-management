import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../shared/models/user.model';
import { UserRequest } from '../../shared/models/user-request';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl: string = 'http://localhost:8080/api/users';

  constructor(private http:HttpClient) {}
  
  getAll(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  getById(userId:number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${userId}`)
  }

  create(user : UserRequest): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }

  update(userId : number, user : User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${userId}`, user);
  }

  patch(userId : number, user : UserRequest): Observable<User> {
    return this.http.patch<User>(`${this.apiUrl}/${userId}`, user);
  }

  delete(userId : number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${userId}`);
  }
}
