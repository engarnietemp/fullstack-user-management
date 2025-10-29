import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormControl } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink, Router } from '@angular/router';
import { User } from '../../../shared/models/user.model';
import { UserType } from '../../../shared/models/user-type.model';
import { UserService } from '../../../core/services/user.service.ts';
import { UserTypeService } from '../../../core/services/user-type.service.ts';
import { UserRequest } from '../../../shared/models/user-request';


@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './user-form.html',
  styleUrl: './user-form.css',
})
export class UserForm implements OnInit {
  userForm: FormGroup;
  userTypes: UserType[] = [];
  isEditMode = false;
  userId: number | null = null;
  loading = false;

  constructor(
    private userTypeService: UserTypeService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    // Init of the Form Validation
    this.userForm = new FormGroup(
      {
        name: new FormControl('', [Validators.required]),
        firstname: new FormControl('', [Validators.required]),
        email: new FormControl('', [Validators.required, Validators.email]),
        userTypeId: new FormControl('', [Validators.required])
      });
  }

  ngOnInit(): void {
    // DROPDOWN INIT
    this.loadUserTypes();
    
    // POST vs PATCH
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.userId = Number(id);
      this.loadUser(this.userId);
    }
  }

  loadUserTypes(): void {
    this.userTypeService.getAll().subscribe({
      next: (data) => {
        this.userTypes = data
      },
      error: (error) => {
        console.error('Error while loading UserTypes : ', error);
      }
    })
  }

  loadUser(id: number): void {
    this.loading = true;
    this.userService.getById(id).subscribe({
      next: (userData) => {
        
        this.userForm.patchValue({
          name: userData.name,
          firstname: userData.firstname,
          email: userData.email,
          userTypeId: userData.userType.id
        });
        this.loading = false;
      },
      error: (error) => {
        console.error('Error: ', error);
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.userForm.invalid) {
      alert('Please, fill all fields correctly.');
      this.userForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    const formValue = this.userForm.value;

    const userData: UserRequest = {
      name: formValue.name,
      firstname: formValue.firstname,
      email: formValue.email,
      userType: { id: formValue.userTypeId }
    };

    if (this.isEditMode && this.userId) {
      this.userService.patch(this.userId, userData).subscribe({
        next: () => this.router.navigate(['/users']),
        error: (error) => {
          alert(error.error.message || 'Error while updating.');
          this.loading = false;
        }
      });
    } else {
      this.userService.create(userData as User).subscribe({
        next: () => this.router.navigate(['/users']),
        error: (error) => {
          alert(error.error.message || 'Error while creating.');
          this.loading = false;
        }
      });
    }
  }
}
