import { Routes } from '@angular/router';

import { UserTypeForm } from './features/user-types/user-type-form/user-type-form';
import { UserForm } from './features/users/user-form/user-form';
import { UserList } from './features/users/user-list/user-list';
import { UserTypeList } from './features/user-types/user-type-list/user-type-list';

export const routes: Routes = [
    {path: '', redirectTo: '/users', pathMatch: 'full'},
    { path: 'users', component: UserList },
    { path: 'users/new', component: UserForm },
    { path: 'users/:id', component: UserForm },
    { path: 'user-types', component: UserTypeList },
    { path: 'user-types/new', component: UserTypeForm },
    { path: 'user-types/:id', component: UserTypeForm },
    { path: '**', redirectTo: '/users' }    
];
