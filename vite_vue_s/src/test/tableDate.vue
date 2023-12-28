<template>
  <thead>
  <tr>
    <th>Date Start</th>
    <th>Date End</th>
    <th>School</th>
    <th>Major</th>
  </tr>
  </thead>
  <tbody>
  <tr v-for="(row, index) in tableData" :key="index">
    <td><input type="date" v-model="row.startenddate.start"/></td>
    <td><input type="date" v-model="row.startenddate.end"/></td>
    <td><input type="text" v-model="row.school"/></td>
    <td><input type="text" v-model="row.major"/></td>
  </tr>
  </tbody>
</template>

<script>
import {defineComponent} from 'vue';
import {randomDate} from '../utils/Date.js'
import axios from "axios";

export default defineComponent({
  data() {
    return {
      tableData: [
        {
          startenddate: {
            start: null,
            end: null,
          },
          school: 'university',
          major: 'computer',
        },
        {
          startenddate: {
            start: null,
            end: null,
          },
          school: '',
          major: '',
        },
        {
          startenddate: {
            start: null,
            end: null,
          },
          school: '',
          major: '',
        },
      ],
    }
  },
  async mounted() {
    try {
      const response = await axios.get('/your-endpoint');
      this.tableData = response.data;
    } catch (error) {
      console.error(error);
    }
  },
  methods: {
    async submitData() {
      try {
        const response = await axios.post('/eduBackground', this.tableData);
        console.log(response.data);
      } catch (error) {
        console.error(error);
      }
    },
  },
});
</script>