import { createStore } from 'vuex';
import axios from 'axios';

const store = createStore({
    state() {
        return {
            user: null,
        };
    },
    mutations: {
        setUser(state, user) {
            state.user = user;
        },
    },
    actions: {
        async fetchUser({ commit }) {
            const response = await axios.get('/me');
            commit('setUser', response.data);
        },
    },
});