import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { UserType } from '../../../shared/models/user-type.model';
import { CommonModule } from '@angular/common';
import { UserTypeService } from '../../../core/services/user-type.service.ts';


@Component({
  selector: 'app-user-type-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './user-type-list.html',
  styleUrl: './user-type-list.css',
})
export class UserTypeList {
  userTypes: UserType[] =  [];
  loading: boolean = true;
  errorMessage: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  constructor(private userTypeService : UserTypeService) {

  }
      ngOnInit(): void {
      this.loadUserTypes();
  }
  
  sortById(): void {
    this.userTypes.sort((a, b) => {
      const comparison = (a.id || 0) - (b.id || 0);
      return this.sortDirection === 'asc' ? comparison : -comparison;
    });
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
  }

  loadUserTypes(): void {
    this.userTypeService.getAll().subscribe({
      next: (data) => {
        this.userTypes = data;
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = 'Error while loading UserTypes';
        this.loading = false;
        console.error('Error : ',error);
      }
    })
  }

  deleteUserType(id: number): void {
    if (confirm('Are you sure to delete this User Type ?')) {
      this.userTypeService.delete(id).subscribe({
        next: () => {
          this.loadUserTypes();
        },
        error: (error) => {
          alert('Erreur while deleting : ' + error.error.message);
          console.error('Erreur:', error);
        }
      });
    }
  }
}
