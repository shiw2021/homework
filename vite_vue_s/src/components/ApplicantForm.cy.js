import ApplicantForm from './ApplicantForm.vue'

describe('<ApplicantForm />', () => {
  it('renders', () => {
    // see: https://on.cypress.io/mounting-vue
    cy.mount(ApplicantForm)
  })
})