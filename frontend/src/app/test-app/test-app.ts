import { Component, inject, OnInit } from '@angular/core';
import { TestService } from '../services/test-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-test-app',
  imports: [],
  templateUrl: './test-app.html',
  styleUrl: './test-app.css',
})
export class TestApp implements OnInit {
  private testService = inject(TestService);
  private router = inject(Router);
  ngOnInit(): void {
    this.testService.appTest().subscribe(data=>{
      if(data.msg === "OK"){
        this.router.navigate(["home"]);
      }
    })
  }

}
