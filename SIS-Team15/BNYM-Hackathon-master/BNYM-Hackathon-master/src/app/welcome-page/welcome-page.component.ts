import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.css']
})
export class WelcomePageComponent {
  constructor(private router: Router){
  }
  redirectAdmin(){
    this.router.navigate(["/login"]);
  }
  redirectStudent(){
    this.router.navigate(["/student-login"]);
  }

}
