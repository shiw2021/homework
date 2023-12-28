import {createRouter, createWebHistory} from 'vue-router'
import AdminHome from '../layout/index.vue'
import Login from '../components/Login.vue'
import Department from "../components/Department.vue";
import Button from "../test/button.vue";
import information from "../test/information.vue";
import layout_menu from "../test/layout_menu.vue";
import account from "../components/Account.vue";
import UserIndex from "../layout/userIndex.vue";
import ApplicantForm from "../components/ApplicantForm.vue";
import tableDate from "../test/tableDate.vue";
import ApplicantTable from "../components/ApplicantTable.vue";
import ApplicantView from "../components/ApplicantView.vue";
import AvatarUploader from "../components/AvatarUploader.vue";
import RecordWork from "../components/RecordWork.vue";

const Test = {
    template: `
    <div >
      <h2>test</h2>
      <router-view></router-view> 
    </div>
  `,
}

const routes = [
    {
        path: '/admin',
        name: 'AdminHome',
        component: AdminHome,
        meta: {requiresAuth: true},
        children: [
            {
                path: '/admin/department',
                name: 'Department',
                component: Department,
                meta: {requiresAuth: true},
            },
            {
                path: '/test2',
                component: Test,
            },
            {
                path: '/admin/account',
                component: account,
            }
        ]
    },
    {
        path: '/hr',
        name: 'HR',
        component: UserIndex,
        meta: {requiresAuth: true},
        children: [
            {
                path: '/hr/applicantForm',
                name: 'ApplicantForm',
                component: ApplicantForm,
            },
            {
                path: '/hr/applicantTable',
                name: 'ApplicantTable',
                component: ApplicantTable,
            },
            {
                path: '/hr/applicantView',
                name: 'ApplicantView',
                component: ApplicantView,
            }
        ]
    },
    {
        path: '/recorder',
        name: 'Recorder',
        component: UserIndex,
        children: [
            {
                path: '/Recorder/work',
                name: 'RecordWork',
                component: RecordWork,
            },

        ]
    },
    {
        path: '/',
        redirect: {name: 'Login'}
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/test',
        name: 'Test',
        component: Test,
        children: [
            {
                path: '/department2',
                component: Department,
            },
            {
                path: '/button',
                component: Button,
            },
            {
                path: '/slot',
                component: information,
            },
            {
                path: '/new_menu',
                component: layout_menu,
            },
            {
                path: '/tabledate',
                component: tableDate,
            },

        ]
    },
    {
        path: '/avatarupload',
        component: AvatarUploader,
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})
router.beforeEach((to, from, next) => {
    const requiresAuth = to.meta.requiresAuth
    const isLoggedIn = localStorage.getItem('isLoggedIn')
    if (requiresAuth && !(isLoggedIn === 'true')) {
        next('/login')
    } else {
        next()
    }
})
router.beforeEach((to, from, next) => {

    const userInfo = JSON.parse(localStorage.getItem('userinfo'))

    if (userInfo !== null && userInfo.departmentId !== 2 && to.name === 'ApplicantView') {
        next({name: 'RecordWork'})
    } else {
        next()
    }
})

export default router
