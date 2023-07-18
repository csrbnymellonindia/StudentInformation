import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LocalService } from '../localStorage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email:string;
  password:string;

  constructor(private router: Router,private localService : LocalService){
  }

  logInUser(){
    if(this.email=='admin@gmail.com' && this.password == 'admin'){
      this.localService.saveData('id','1234');
      this.router.navigate(['/admin']);
    }
    else{
      const error_msg = document.getElementById('admin-login-error');
      error_msg.innerHTML="Invalid Credentionals";
      error_msg.setAttribute("class","alert alert-danger");
      console.log("User unauthorized");
    }
  }

}


