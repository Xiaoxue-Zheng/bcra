import { browser, element, by, ExpectedConditions as ec } from 'protractor';

import { NavBarPage, SignInPage, PasswordPage, SettingsPage } from '../page-objects/jhi-page-objects';

const expect = chai.expect;

describe('account', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let passwordPage: PasswordPage;
  let settingsPage: SettingsPage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage(true);
  });

  it('should fail to login with bad password', async () => {
    const expect1 = 'High Risk Young Women Study';
    const value1 = await element(by.css('h1')).getText();
    expect(value1).to.eq(expect1);
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignNormalUserInUsing('admin', 'foo');

    const expect2 = 'Failed to sign in! Please check your credentials and try again.';
    const value2 = await element(by.css('.alert-danger')).getText();
    expect(value2).to.eq(expect2);
  });

  it('should fail to login with participant user', async () => {
    await browser.get('/');
    const expect1 = 'High Risk Young Women Study';
    const value1 = await element(by.css('h1')).getText();
    expect(value1).to.eq(expect1);
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignNormalUserInUsing('test9', 'user');

    const expect2 =
      'Failed to sign in! You are not authorized to access this page. If you think this is a mistake, please contact the study team.';
    const value2 = await element(by.css('.alert-danger')).getText();
    expect(value2).to.eq(expect2);
  });

  it('should login fail with admin account but wrong pin', async () => {
    await browser.get('/');
    signInPage = await navBarPage.getSignInPage();

    const expect1 = 'Login';
    const value1 = await element(by.className('username-label')).getText();
    expect(value1).to.eq(expect1);
    await signInPage.twoFactorSignInWithPin('admin', 'admin', '111111');

    const expect2 = '1 failed pin validation attempt(s)!\nLogin for user will be locked after 5 failed attempts';
    await browser.wait(ec.visibilityOf(element(by.id('validate-pin-fail-message'))));
    const value2 = await element(by.id('validate-pin-fail-message')).getText();
    expect(value2).to.eq(expect2);
  });

  it('should login successfully with admin account', async () => {
    await browser.get('/');
    signInPage = await navBarPage.getSignInPage();

    const expect1 = 'Login';
    const value1 = await element(by.className('username-label')).getText();
    expect(value1).to.eq(expect1);
    await signInPage.twoFactorSignIn('admin', 'admin');

    const expect2 = 'You are logged in as user "admin".';
    await browser.wait(ec.visibilityOf(element(by.id('home-logged-message'))));
    const value2 = await element(by.id('home-logged-message')).getText();
    expect(value2).to.eq(expect2);
  });

  it('should be able to update settings', async () => {
    settingsPage = await navBarPage.getSettingsPage();

    const expect1 = 'User settings for [admin]';
    const value1 = await settingsPage.getTitle();
    expect(value1).to.eq(expect1);
    await settingsPage.save();

    const expect2 = 'Settings saved!';
    const alert = element(by.css('.alert-success'));
    const value2 = await alert.getText();
    expect(value2).to.eq(expect2);
  });

  it('should fail to update password when using incorrect current password', async () => {
    passwordPage = await navBarPage.getPasswordPage();

    expect(await passwordPage.getTitle()).to.eq('Password for [admin]');

    await passwordPage.setCurrentPassword('wrong_current_password');
    await passwordPage.setPassword('new_password');
    await passwordPage.setConfirmPassword('new_password');
    await passwordPage.save();

    const expect2 = 'An error has occurred! The password could not be changed.';
    const alert = element(by.css('.alert-danger'));
    const value2 = await alert.getText();
    expect(value2).to.eq(expect2);
    settingsPage = await navBarPage.getSettingsPage();
  });

  it('should be able to update password', async () => {
    passwordPage = await navBarPage.getPasswordPage();

    expect(await passwordPage.getTitle()).to.eq('Password for [admin]');

    await passwordPage.setCurrentPassword('admin');
    await passwordPage.setPassword('newpassword');
    await passwordPage.setConfirmPassword('newpassword');
    await passwordPage.save();

    const expect2 = 'Password changed!';
    const alert = element(by.css('.alert-success'));
    const value2 = await alert.getText();
    expect(value2).to.eq(expect2);
    await navBarPage.autoSignOut();
    await navBarPage.goToSignInPage();
    await signInPage.twoFactorSignIn('admin', 'newpassword');

    // change back to default
    await navBarPage.goToPasswordMenu();
    await passwordPage.setCurrentPassword('newpassword');
    await passwordPage.setPassword('admin');
    await passwordPage.setConfirmPassword('admin');
    await passwordPage.save();
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
