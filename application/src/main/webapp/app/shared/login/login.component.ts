import { Component, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { LoginService } from 'app/core/login/login.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';

@Component({
  selector: 'jhi-login-modal',
  templateUrl: './login.component.html'
})
export class JhiLoginModalComponent implements AfterViewInit {
  authenticationError: boolean;
  authenticationErrorMessage: string;
  twoFactorAuthentication: boolean;
  showFailedPinValidationAlert: boolean;
  failedPinValidationAttempts: number;

  loginForm = this.fb.group({
    username: [''],
    password: [''],
    pin: [''],
    rememberMe: [false]
  });

  constructor(
    private eventManager: JhiEventManager,
    private loginService: LoginService,
    private stateStorageService: StateStorageService,
    private elementRef: ElementRef,
    private renderer: Renderer,
    private router: Router,
    public activeModal: NgbActiveModal,
    private fb: FormBuilder
  ) {}

  ngAfterViewInit() {
    setTimeout(() => this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#username'), 'focus', []), 0);
  }

  cancel() {
    this.authenticationError = false;
    this.authenticationErrorMessage = '';
    this.twoFactorAuthentication = false;
    this.showFailedPinValidationAlert = false;
    this.failedPinValidationAttempts = 0;
    this.loginForm.patchValue({
      username: '',
      password: '',
      pin: ''
    });

    this.activeModal.dismiss('cancel');
  }

  login() {
    this.loginService
      .twoFactorAuthInit(this.loginForm.get('username').value, this.loginForm.get('password').value)
      .then(res => {
        this.authenticationError = false;
        if (res) {
          this.twoFactorAuthentication = true;
        } else {
          this.loginService
            .login({
              username: this.loginForm.get('username').value,
              password: this.loginForm.get('password').value,
              rememberMe: this.loginForm.get('rememberMe').value
            })
            .then((response: any) => {
              this.loginSuccess();
            })
            .catch(error => {
              this.loginFail(error);
            });
        }
      })
      .catch(error => {
        this.loginFail(error);
      });
  }

  loginSuccess() {
    this.authenticationError = false;
    this.showFailedPinValidationAlert = false;
    this.activeModal.dismiss('login success');
    if (this.router.url === '/register' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
      this.router.navigate(['']);
    }

    this.eventManager.broadcast({
      name: 'authenticationSuccess',
      content: 'Sending Authentication Success'
    });

    // previousState was set in the authExpiredInterceptor before being redirected to login modal.
    // since login is successful, go to stored previousState and clear previousState
    const redirect = this.stateStorageService.getUrl();
    if (redirect) {
      this.stateStorageService.storeUrl(null);
      this.router.navigateByUrl(redirect);
    }
  }

  loginFail(error) {
    this.authenticationError = true;
    if (error.error && error.error.detail) {
      if (error.error.detail === 'Bad credentials') {
        this.authenticationErrorMessage = 'Please check your credentials and try again.';
      } else {
        this.authenticationErrorMessage = error.error.detail;
      }
    } else {
      this.authenticationErrorMessage = 'Please check your credentials and try again.';
    }
  }

  validatePin() {
    if (this.failedPinValidationAttempts === 5) {
      this.authenticationError = true;
      this.authenticationErrorMessage =
        'Login is not permitted. User has had five failed pin validation attempts. Please try again after 30 minutes';
    } else {
      this.loginService
        .login({
          username: this.loginForm.get('username').value,
          password: this.loginForm.get('password').value,
          pin: this.loginForm.get('pin').value,
          rememberMe: this.loginForm.get('rememberMe').value
        })
        .then((response: any) => {
          this.authenticationError = false;
          if (response !== null && response.authenticated !== null && !response.authenticated) {
            this.showFailedPinValidationAlert = true;
            this.failedPinValidationAttempts = response.failedAttempts;
          } else {
            this.showFailedPinValidationAlert = false;
            this.loginSuccess();
          }
        })
        .catch(error => {
          this.loginFail(error);
        });
    }
  }

  register() {
    this.activeModal.dismiss('to state register');
    this.router.navigate(['/register']);
  }

  requestResetPassword() {
    this.activeModal.dismiss('to state requestReset');
    this.router.navigate(['/reset', 'request']);
  }
}
