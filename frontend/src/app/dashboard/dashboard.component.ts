import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  students : any


  constructor(private router: Router, private http:HttpClient) { 

    this.http.get('http://localhost:3307/hello').subscribe((result:any)=>{
      if (result) {
        console.log(result);
        localStorage.setItem("students", JSON.stringify(result));
        this.students = JSON.parse(localStorage.getItem("students") || " ");
        console.log(this.students);
        
        
      }
    })
  }




  logout(){
    localStorage.clear;
    this.router.navigateByUrl("");

  }

}
