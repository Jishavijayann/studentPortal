import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../services/api.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json ; charset=utf-8',
  }),
};

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  error: any

  registerForm = this.fb.group({
    username: ["", [Validators.required, Validators.pattern("[a-zA-Z]*"), Validators.minLength(4)]],
    branch: ["", [Validators.required, Validators.pattern("[a-zA-Z]*")]],
    regno: ["", [Validators.required, Validators.pattern("[0-9]*")]],
    english: ["", [Validators.required, Validators.pattern("[0-9]*")]],
    physics: ["", [Validators.required, Validators.pattern("[0-9]*")]],
    chemistry: ["", [Validators.required, Validators.pattern("[0-9]*")]],
    total: ["", [Validators.required, Validators.pattern("[0-9]*")]]

  })



  constructor(private router: Router, private fb: FormBuilder, private api: ApiService, private http :HttpClient) { }

  register() {
    if (this.registerForm.valid) {
      


       var username = this.registerForm.value.username;
       var branch = this.registerForm.value.branch;
      var regno = this.registerForm.value.regno;
      var english = this.registerForm.value.english;
      var physics = this.registerForm.value.physics;
      var chemistry = this.registerForm.value.chemistry;
      var total = this.registerForm.value.total;

      console.log(username);
      

      const body = {
        "student_name" :String(username ),
        "branch" :String( branch),
        "register_number" : String(regno),
        "english" : String(english),
        "physics ": String(physics),
        "chemistry" : String(chemistry) ,
        "total_mark" : String(total) 
      }
      console.log(body);
      

      this.api.register(username,branch,regno,english,physics,chemistry,total).subscribe((result:any)=>{
        console.log(result);
        alert(result.message);
        this.router.navigateByUrl("");
           },
             (result:any)=>{
             this.error = result.error.message;
      }
      )
    }else{
      this.error = "Invalid Form"
    }

  }

}
