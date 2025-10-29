import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { UserType } from '../../shared/models/user-type.model';

@Injectable({
  providedIn: 'root'
})
export class UserTypeService {
  private apiUrl:string = 'http://localhost:8080/api/user-types'

  constructor(private http:HttpClient) {}

  getAll(): Observable<UserType[]> {
    return this.http.get<UserType[]>(this.apiUrl);
  }

  getById(userTypeId:number): Observable<UserType> {
    return this.http.get<UserType>(`${this.apiUrl}/${userTypeId}`)
  }

  create(userType : UserType): Observable<UserType> {
    return this.http.post<UserType>(this.apiUrl, userType);
  }

  update(userTypeId : number, userType : UserType): Observable<UserType> {
    return this.http.put<UserType>(`${this.apiUrl}/${userTypeId}`, userType);
  }

  delete(userTypeId : number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${userTypeId}`);
  }
}