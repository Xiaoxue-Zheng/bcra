/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IdentifiableDataComponentsPage, IdentifiableDataDeleteDialog, IdentifiableDataUpdatePage } from './identifiable-data.page-object';

const expect = chai.expect;

describe('IdentifiableData e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let identifiableDataUpdatePage: IdentifiableDataUpdatePage;
  let identifiableDataComponentsPage: IdentifiableDataComponentsPage;
  let identifiableDataDeleteDialog: IdentifiableDataDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load IdentifiableData', async () => {
    await navBarPage.goToEntity('identifiable-data');
    identifiableDataComponentsPage = new IdentifiableDataComponentsPage();
    await browser.wait(ec.visibilityOf(identifiableDataComponentsPage.title), 5000);
    expect(await identifiableDataComponentsPage.getTitle()).to.eq('Identifiable Data');
  });

  it('should load create IdentifiableData page', async () => {
    await identifiableDataComponentsPage.clickOnCreateButton();
    identifiableDataUpdatePage = new IdentifiableDataUpdatePage();
    expect(await identifiableDataUpdatePage.getPageTitle()).to.eq('Create or edit a Identifiable Data');
    await identifiableDataUpdatePage.cancel();
  });

  it('should create and save IdentifiableData', async () => {
    const nbButtonsBeforeCreate = await identifiableDataComponentsPage.countDeleteButtons();

    await identifiableDataComponentsPage.clickOnCreateButton();
    await promise.all([
      identifiableDataUpdatePage.setNhsNumberInput('nhsNumber'),
      identifiableDataUpdatePage.setDateOfBirthInput('2000-12-31'),
      identifiableDataUpdatePage.setFirstnameInput('firstname'),
      identifiableDataUpdatePage.setSurnameInput('surname'),
      identifiableDataUpdatePage.setEmailInput('email'),
      identifiableDataUpdatePage.setAddress1Input('address1'),
      identifiableDataUpdatePage.setAddress2Input('address2'),
      identifiableDataUpdatePage.setAddress3Input('address3'),
      identifiableDataUpdatePage.setAddress4Input('address4'),
      identifiableDataUpdatePage.setAddress5Input('address5'),
      identifiableDataUpdatePage.setPostcodeInput('postcode'),
      identifiableDataUpdatePage.setPracticeNameInput('practiceName')
    ]);
    expect(await identifiableDataUpdatePage.getNhsNumberInput()).to.eq('nhsNumber', 'Expected NhsNumber value to be equals to nhsNumber');
    expect(await identifiableDataUpdatePage.getDateOfBirthInput()).to.eq(
      '2000-12-31',
      'Expected dateOfBirth value to be equals to 2000-12-31'
    );
    expect(await identifiableDataUpdatePage.getFirstnameInput()).to.eq('firstname', 'Expected Firstname value to be equals to firstname');
    expect(await identifiableDataUpdatePage.getSurnameInput()).to.eq('surname', 'Expected Surname value to be equals to surname');
    expect(await identifiableDataUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    expect(await identifiableDataUpdatePage.getAddress1Input()).to.eq('address1', 'Expected Address1 value to be equals to address1');
    expect(await identifiableDataUpdatePage.getAddress2Input()).to.eq('address2', 'Expected Address2 value to be equals to address2');
    expect(await identifiableDataUpdatePage.getAddress3Input()).to.eq('address3', 'Expected Address3 value to be equals to address3');
    expect(await identifiableDataUpdatePage.getAddress4Input()).to.eq('address4', 'Expected Address4 value to be equals to address4');
    expect(await identifiableDataUpdatePage.getAddress5Input()).to.eq('address5', 'Expected Address5 value to be equals to address5');
    expect(await identifiableDataUpdatePage.getPostcodeInput()).to.eq('postcode', 'Expected Postcode value to be equals to postcode');
    expect(await identifiableDataUpdatePage.getPracticeNameInput()).to.eq(
      'practiceName',
      'Expected PracticeName value to be equals to practiceName'
    );
    await identifiableDataUpdatePage.save();
    expect(await identifiableDataUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await identifiableDataComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last IdentifiableData', async () => {
    const nbButtonsBeforeDelete = await identifiableDataComponentsPage.countDeleteButtons();
    await identifiableDataComponentsPage.clickOnLastDeleteButton();

    identifiableDataDeleteDialog = new IdentifiableDataDeleteDialog();
    expect(await identifiableDataDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Identifiable Data?');
    await identifiableDataDeleteDialog.clickOnConfirmButton();

    expect(await identifiableDataComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
