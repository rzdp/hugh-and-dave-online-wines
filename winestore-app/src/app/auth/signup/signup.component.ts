import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../service/auth.service';
import {SignupRequest} from '../../model/request/signup.request';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {SignupResponse} from '../../model/response/signup.response';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  signupForm = new FormGroup({
    firstName: new FormControl('', [
      Validators.required,
      Validators.maxLength(50),
      Validators.pattern('^[A-Za-z\\u00f1\\u00d1 ]+$')
    ]),
    lastName: new FormControl('', [
      Validators.required,
      Validators.maxLength(50),
      Validators.pattern('^[A-Za-z\\u00f1\\u00d1 ]+$')
    ]),
    mobile: new FormControl('', [
      Validators.required,
      Validators.minLength(10),
      Validators.maxLength(10),
      Validators.pattern('^[0-9]*$')
    ]),
    email: new FormControl('', [
      Validators.required,
      Validators.maxLength(50),
      Validators.email
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(128),
    ]),
  });

  get firstName() {
    return this.signupForm.get('firstName');
  }

  get lastName() {
    return this.signupForm.get('lastName');
  }

  get mobile() {
    return this.signupForm.get('mobile');
  }

  get email() {
    return this.signupForm.get('email');
  }

  get password() {
    return this.signupForm.get('password');
  }

  constructor(private authService: AuthService,
              private toastr: ToastrService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
  }

  signUp() {
    const request = this.signupForm.value as SignupRequest;
    this.authService.signup(request).subscribe(
      response => {
        const data = response.data as SignupResponse;
        this.toastr.success(response.message, 'Sign up').onShown
          .subscribe(toast => {
            this.router.navigate([data.userId, 'verification'], {
              relativeTo: this.route,
              queryParams: { firstName: data.firstName, lastName: data.lastName }
            });
          });
      },
      error => {
        this.toastr.error(error.error.message, 'Sign up');
      }
    );
  }
}
