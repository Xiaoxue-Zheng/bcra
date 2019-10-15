/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ReferralConditionComponentsPage,
  ReferralConditionDeleteDialog,
  ReferralConditionUpdatePage
} from './referral-condition.page-object';

const expect = chai.expect;

describe('ReferralCondition e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let referralConditionUpdatePage: ReferralConditionUpdatePage;
  let referralConditionComponentsPage: ReferralConditionComponentsPage;
  let referralConditionDeleteDialog: ReferralConditionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ReferralConditions', async () => {
    await navBarPage.goToEntity('referral-condition');
    referralConditionComponentsPage = new ReferralConditionComponentsPage();
    await browser.wait(ec.visibilityOf(referralConditionComponentsPage.title), 5000);
    expect(await referralConditionComponentsPage.getTitle()).to.eq('Referral Conditions');
  });

  it('should load create ReferralCondition page', async () => {
    await referralConditionComponentsPage.clickOnCreateButton();
    referralConditionUpdatePage = new ReferralConditionUpdatePage();
    expect(await referralConditionUpdatePage.getPageTitle()).to.eq('Create or edit a Referral Condition');
    await referralConditionUpdatePage.cancel();
  });

  it('should create and save ReferralConditions', async () => {
    const nbButtonsBeforeCreate = await referralConditionComponentsPage.countDeleteButtons();

    await referralConditionComponentsPage.clickOnCreateButton();
    await promise.all([
      referralConditionUpdatePage.setAndGroupInput('5'),
      referralConditionUpdatePage.typeSelectLastOption(),
      referralConditionUpdatePage.questionIdentifierSelectLastOption(),
      referralConditionUpdatePage.itemIdentifierSelectLastOption(),
      referralConditionUpdatePage.setNumberInput('5'),
      referralConditionUpdatePage.questionSelectLastOption()
    ]);
    expect(await referralConditionUpdatePage.getAndGroupInput()).to.eq('5', 'Expected andGroup value to be equals to 5');
    expect(await referralConditionUpdatePage.getNumberInput()).to.eq('5', 'Expected number value to be equals to 5');
    await referralConditionUpdatePage.save();
    expect(await referralConditionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await referralConditionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ReferralCondition', async () => {
    const nbButtonsBeforeDelete = await referralConditionComponentsPage.countDeleteButtons();
    await referralConditionComponentsPage.clickOnLastDeleteButton();

    referralConditionDeleteDialog = new ReferralConditionDeleteDialog();
    expect(await referralConditionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Referral Condition?');
    await referralConditionDeleteDialog.clickOnConfirmButton();

    expect(await referralConditionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
