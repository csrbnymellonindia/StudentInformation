import { Component, OnInit } from '@angular/core';
import { StudentService } from '../student.service';
import { Student } from '../student';
import { LocalService } from '../localStorage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-personal-student-page',
  templateUrl: './personal-student-page.component.html',
  styleUrls: ['./personal-student-page.component.css']
})
export class PersonalStudentPageComponent implements OnInit{
  public data: Student;
  constructor(private studentService: StudentService,private localService : LocalService,private router: Router) {}
  ngOnInit(): void {

     this.data= this.studentService.getData();
    console.log(this.data);
    console.log(this.localService.getData('id'));
    if((this.data==undefined )|| (this.localService.getData('id')!=this.data.studentCode)){
      this.router.navigate(['/student-login']);
    }
  }
  
}
