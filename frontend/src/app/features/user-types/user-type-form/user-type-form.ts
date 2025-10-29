import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { UserTypeService } from '../../../core/services/user-type.service.ts';
import { UserType } from '../../../shared/models/user-type.model';

@Component({
  selector: 'app-user-type-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './user-type-form.html',
  styleUrl: './user-type-form.css'
})
export class UserTypeForm implements OnInit {
  userTypeForm: FormGroup;
  isEditMode: boolean = false;
  userTypeId: number | null = null;
  loading: boolean = false;

  constructor(
    private userTypeService: UserTypeService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.userTypeForm = new FormGroup({
      label: new FormControl('', [Validators.required])
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.userTypeId = Number(id);
      this.loadUserType(this.userTypeId);
    }
  }

  loadUserType(id: number): void {
    this.loading = true;
    this.userTypeService.getById(id).subscribe({
      next: (userType) => {
        this.userTypeForm.patchValue({
          label: userType.label
        });
        this.loading = false;
      },
      error: (error) => {
        alert('Error while loading User Type');
        console.error(error);
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.userTypeForm.invalid) {
      alert('Field Label is mandatory.');
      this.userTypeForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    const formValue = this.userTypeForm.value;
    const userTypeData: UserType = { label: formValue.label };

    if (this.isEditMode && this.userTypeId) {
      this.userTypeService.update(this.userTypeId, userTypeData).subscribe({
        next: () => this.router.navigate(['/user-types']),
        error: (error) => {
          alert(error.error.message || 'Error while updating.');
          this.loading = false;
        }
      });
    } else {
      this.userTypeService.create(userTypeData).subscribe({
        next: () => this.router.navigate(['/user-types']),
        error: (error) => {
          alert(error.error.message || 'Error while creating.');
          this.loading = false;
        }
      });
    }
  }
}