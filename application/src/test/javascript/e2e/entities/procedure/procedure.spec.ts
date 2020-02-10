/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProcedureComponentsPage, ProcedureDeleteDialog, ProcedureUpdatePage } from './procedure.page-object';

const expect = chai.expect;

describe('Procedure e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let procedureUpdatePage: ProcedureUpdatePage;
  let procedureComponentsPage: ProcedureComponentsPage;
  /*let procedureDeleteDialog: ProcedureDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Procedures', async () => {
    await navBarPage.goToEntity('procedure');
    procedureComponentsPage = new ProcedureComponentsPage();
    await browser.wait(ec.visibilityOf(procedureComponentsPage.title), 5000);
    expect(await procedureComponentsPage.getTitle()).to.eq('Procedures');
  });

  it('should load create Procedure page', async () => {
    await procedureComponentsPage.clickOnCreateButton();
    procedureUpdatePage = new ProcedureUpdatePage();
    expect(await procedureUpdatePage.getPageTitle()).to.eq('Create or edit a Procedure');
    await procedureUpdatePage.cancel();
  });

  /* it('should create and save Procedures', async () => {
        const nbButtonsBeforeCreate = await procedureComponentsPage.countDeleteButtons();

        await procedureComponentsPage.clickOnCreateButton();
        await promise.all([
            procedureUpdatePage.consentResponseSelectLastOption(),
            procedureUpdatePage.riskAssessmentResponseSelectLastOption(),
        ]);
        await procedureUpdatePage.save();
        expect(await procedureUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await procedureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last Procedure', async () => {
        const nbButtonsBeforeDelete = await procedureComponentsPage.countDeleteButtons();
        await procedureComponentsPage.clickOnLastDeleteButton();

        procedureDeleteDialog = new ProcedureDeleteDialog();
        expect(await procedureDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Procedure?');
        await procedureDeleteDialog.clickOnConfirmButton();

        expect(await procedureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
