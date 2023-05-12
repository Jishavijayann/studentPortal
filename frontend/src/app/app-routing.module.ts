import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UpdateComponent } from './update/update.component';

const routes: Routes = [
  {
    path: "" , component : LoginComponent
  }, 
  {
    path : "register", component : RegisterComponent
  },
  {
    path : "dashboard", component : DashboardComponent
  },
  {
    path : "update", component : UpdateComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
