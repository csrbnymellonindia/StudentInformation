import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from './student';
import { environment } from 'src/environments/environment';

@Injectable({providedIn: 'root'})
export class StudentService {
  private apiServerUrl= environment.apiBaseUrl;
  private data: Student;

  constructor(private http: HttpClient) { }
  getData(): Student {
    return this.data;
  }

  setData(data: Student) {
    this.data = data;
  }
  public getStudents():Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/student/all`);
  }

  public addStudent(student: Student): Observable<Student> {
    return this.http.post<Student>(`${this.apiServerUrl}/student/add`, student);
  }

  public updateStudent(student: Student): Observable<Student> {
    return this.http.put<Student>(`${this.apiServerUrl}/student/update`, student);
  }

  public deleteStudent(studentId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/student/delete/${studentId}`);
}
  public viewStudent(studentId: number):Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/student/view/${studentId}`);
  }
  public loginStudent(email:string,password:string): Observable<Student> {
    return this.http.get<Student>(`${this.apiServerUrl}/student/viewStudentEmail/${email}/${password}`);
  }
}
