import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AuthService} from '../../service/auth.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-signup-verification',
  templateUrl: './signup-verification.component.html',
  styleUrls: ['./signup-verification.component.scss']
})
export class SignupVerificationComponent implements OnInit {

  private userId: number;

  constructor(private route: ActivatedRoute,
              private authService: AuthService,
              private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.userId = +this.route.snapshot.paramMap.get('id');
  }

  resendEmail() {
    this.authService.notifySignup(this.userId)
      .subscribe(
        response => {
          this.toastr.success(response.message, 'User Verification');
        },
        error => {
          this.toastr.error(error.error.message);
        }
      );
  }
}
