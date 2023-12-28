import Account from './Account.vue'

describe('<Account />', () => {
  it('renders', () => {
    // see: https://on.cypress.io/mounting-vue
    cy.mount(Account)
  })
})