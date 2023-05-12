import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';





const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json ; charset=utf-8',
  }),
};

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor( private http: HttpClient) { }


  register(student_name: any,branch: any,register_number: any,english: any,physics: any,chemistry:any ,total_mark:any){
    const body = {
      "student_name" : String(student_name),
      "branch" : String(branch),
      "register_number" : String(register_number),
      "english" : String(english),
      "physics" : String(physics),
      "chemistry" :String(chemistry) ,
      "total_mark" : String(total_mark)
    }
    
    return this.http.post('http://localhost:3307/register', body, httpOptions);
  }



  login(student_name:any,register_number:any){
    const body = {
      student_name,
      register_number
    }

    return this.http.post('http://localhost:3307/login/', body, httpOptions)

  }



  

  update(student_name: any,register_number: any,branch: any,english: any,physics: any,chemistry:any ,total_mark:any){
    const body = {
      student_name,
      register_number,
      branch,
      english,
      physics,
      chemistry ,
      total_mark
    }
    
    return this.http.post('http://localhost:3307/update/', body, httpOptions);
  }
}
