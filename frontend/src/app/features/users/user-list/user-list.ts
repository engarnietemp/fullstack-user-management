import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { User } from '../../../shared/models/user.model';
import { UserService } from '../../../core/services/user.service.ts';


@Component({
  selector: 'app-user-list',
  imports: [RouterLink, CommonModule],
  templateUrl: './user-list.html',
  styleUrl: './user-list.css',
})
export class UserList {
  users : User[] = [];
  loading: boolean = true;
  errorMessage: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  constructor(private userService : UserService) {}

    ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers():void {
    this.userService.getAll().subscribe({
      next: (data) => {
        this.users = data;
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = 'Error while loading Users';
        this.loading = false;
        console.error('Error: ', error);
      }
    })
  }

  sortByType(): void {
    this.users.sort((a, b) => {
      const labelA = a.userType.label.toLowerCase();
      const labelB = b.userType.label.toLowerCase();
      const comparison = labelA.localeCompare(labelB);
      return this.sortDirection === 'asc' ? comparison : -comparison;
    });
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
  }

  deleteUser(userId:number):void {
    if(confirm('Are you sure you want to delete this user ?')) {
      this.userService.delete(userId).subscribe({
        next: () => {
          this.loadUsers();
        },
        error: (error) => {
          alert('Erreur while deleting : ' + error.error.message);
          console.error('Error:', error);
        }
      })
    }
  }
}
