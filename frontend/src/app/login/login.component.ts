import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../services/api.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  error : any

  loginForm = this.fb.group({
    username: ["", [Validators.required, Validators.pattern("[a-zA-Z0-9]*"), Validators.minLength(4)]],
    regno: ["", [Validators.required, Validators.pattern("[0-9]*")]]
  })
  
  constructor(private router: Router, private fb: FormBuilder, private api : ApiService, private http : HttpClient) { }


  login() {
    if (this.loginForm.valid) {
      var username = this.loginForm.value.username;
      var regno = this.loginForm.value.regno;

      const body = {
        "student_name" :String(username),
        "register_number" : String(regno)
      }

      this.http.post('http://localhost:3307/login', body).subscribe((result:any)=>{
        console.log(result);
        if (result.message) {
          alert(result.message);
          this.router.navigateByUrl("dashboard");
        }else{
          alert(result.error);
        }
        
           },
          
      )
    }else{
      this.error = "Invalid Form"
    }

  }

}
