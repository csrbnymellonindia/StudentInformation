import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AdminComponent } from './admin/admin.component';
import { StudentLoginComponent } from './student-login/student-login.component';
import { PersonalStudentPageComponent } from './personal-student-page/personal-student-page.component';
import { WelcomePageComponent } from './welcome-page/welcome-page.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'student-login', component: StudentLoginComponent },
  { path: 'personal-student-page', component: PersonalStudentPageComponent },
  { path: '', component: WelcomePageComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminComponent,
    StudentLoginComponent,
    PersonalStudentPageComponent,
    WelcomePageComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule, FormsModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent],

  exports: [RouterModule]
})
export class AppModule { }
