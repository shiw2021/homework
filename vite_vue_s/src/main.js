import { createApp } from 'vue'
import App from './App.vue'
const app = createApp(App)




import * as ElementPlusIconsVue from '@element-plus/icons-vue'
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}


import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
app.use(ElementPlus)

import router from './router'
app.use(router)

import store from "./store/store.js";
app.use(store)

app.mount('#app')
