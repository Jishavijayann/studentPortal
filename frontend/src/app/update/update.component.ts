import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../services/api.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})
export class UpdateComponent {

  
  error : any

  updateForm = this.fb.group({
    username: ["", [Validators.required, Validators.pattern("[a-zA-Z]*"), Validators.minLength(4)]],
    branch: ["", [Validators.required, Validators.pattern("[a-zA-Z]*")]],
    regno: ["", [Validators.required, Validators.pattern("[0-9]*")]],
    english: ["", [Validators.required, Validators.pattern("[0-9]*")]],
    physics: ["", [Validators.required, Validators.pattern("[0-9]*")]],
    chemistry: ["", [Validators.required, Validators.pattern("[0-9]*")]],
    total: ["", [Validators.required, Validators.pattern("[0-9]*")]]

  })



  constructor(private router: Router, private fb: FormBuilder, private api:ApiService, private http : HttpClient) { }

  update() {
    if (this.updateForm.valid) {
      var username = this.updateForm.value.username;
      var branch = this.updateForm.value.branch;
      var regno = this.updateForm.value.regno;
      var english = this.updateForm.value.english;
      var physics = this.updateForm.value.physics;
      var chemistry = this.updateForm.value.chemistry;
      var total = this.updateForm.value.total;

      const body = {
        "student_name" :String(username),
        "register_number" : String(regno),
        "branch" : String(branch),
        "english" : String(english),
        "physics" : String(physics),
        "chemistry" : String(chemistry) ,
        "total_mark" : String(total)
      }
      console.log(body);
      

      this.http.put('http://localhost:3307/update', body).subscribe((result:any)=>{
        console.log(result);
        alert(result.message);
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
