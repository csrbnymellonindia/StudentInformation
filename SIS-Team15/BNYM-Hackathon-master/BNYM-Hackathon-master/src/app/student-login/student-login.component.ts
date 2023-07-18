import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Student } from '../student';
import { StudentService } from '../student.service';
import { LocalService } from '../localStorage.service';

@Component({
  selector: 'app-student-login',
  templateUrl: './student-login.component.html',
  styleUrls: ['./student-login.component.css']
})
export class StudentLoginComponent {
  public student: Student;
  model: any ={}
  email:string;
  password:string;
  constructor(private router: Router,private studentService: StudentService,private localService: LocalService){}

  public loginStudent(email:string,password:string): Student {
    this.studentService.loginStudent(email,password).subscribe(
      (response: Student) => {
        this.student=response;
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    return this.student;
  }

  logInUser(){
    var e= this.model.email;
    var p=this.model.password;
    this.studentService.loginStudent(e,p).subscribe((res:Student)=>{
      this.student=res;
      if(this.student!=undefined){
        this.studentService.setData(this.student);
        this.localService.saveData('id',this.student.studentCode);
        this.router.navigate(["/personal-student-page"]);
      }
      else{
        const error_msg = document.getElementById('student-login-error');
        error_msg.innerHTML="Invalid Credentionals";
        error_msg.setAttribute("class","alert alert-danger");
        console.log("User unauthorized");
      }

    })
  }


}

