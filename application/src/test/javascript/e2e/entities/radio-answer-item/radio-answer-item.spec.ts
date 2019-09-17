/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RadioAnswerItemComponentsPage, RadioAnswerItemDeleteDialog, RadioAnswerItemUpdatePage } from './radio-answer-item.page-object';

const expect = chai.expect;

describe('RadioAnswerItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let radioAnswerItemUpdatePage: RadioAnswerItemUpdatePage;
  let radioAnswerItemComponentsPage: RadioAnswerItemComponentsPage;
  let radioAnswerItemDeleteDialog: RadioAnswerItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RadioAnswerItems', async () => {
    await navBarPage.goToEntity('radio-answer-item');
    radioAnswerItemComponentsPage = new RadioAnswerItemComponentsPage();
    await browser.wait(ec.visibilityOf(radioAnswerItemComponentsPage.title), 5000);
    expect(await radioAnswerItemComponentsPage.getTitle()).to.eq('Radio Answer Items');
  });

  it('should load create RadioAnswerItem page', async () => {
    await radioAnswerItemComponentsPage.clickOnCreateButton();
    radioAnswerItemUpdatePage = new RadioAnswerItemUpdatePage();
    expect(await radioAnswerItemUpdatePage.getPageTitle()).to.eq('Create or edit a Radio Answer Item');
    await radioAnswerItemUpdatePage.cancel();
  });

  it('should create and save RadioAnswerItems', async () => {
    const nbButtonsBeforeCreate = await radioAnswerItemComponentsPage.countDeleteButtons();

    await radioAnswerItemComponentsPage.clickOnCreateButton();
    await promise.all([radioAnswerItemUpdatePage.radioAnswerSelectLastOption()]);
    await radioAnswerItemUpdatePage.save();
    expect(await radioAnswerItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await radioAnswerItemComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last RadioAnswerItem', async () => {
    const nbButtonsBeforeDelete = await radioAnswerItemComponentsPage.countDeleteButtons();
    await radioAnswerItemComponentsPage.clickOnLastDeleteButton();

    radioAnswerItemDeleteDialog = new RadioAnswerItemDeleteDialog();
    expect(await radioAnswerItemDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Radio Answer Item?');
    await radioAnswerItemDeleteDialog.clickOnConfirmButton();

    expect(await radioAnswerItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
