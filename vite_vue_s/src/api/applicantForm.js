import axios from 'axios';

export function getDepartments(search) {
    // 发起获取数据的请求，替换以下 URL 为你的接口地址
    return axios.get('http://localhost:8080/getDepartments?name=' + search).then(response => {
        console.log(response.data);
        return response.data.Departments;
    });
}


export function submitAvatar(avatar) {
    const formData = new FormData()
    formData.append('avatar', avatar)
    return axios.post('http://localhost:8080/avatar', formData, {headers: {"Content-Type": "multipart/form-data"}}).then(response => {
        console.log('submit avatar' + response.status)
        return response.data
    }).catch(error => {
        console.log(error)
        return error.status
    })
}

export function submitSignature(signature) {
    const formData = new FormData()
    formData.append('signature', signature)
    return axios.post('http://localhost:8080/signature', formData, {headers: {"Content-Type": "multipart/form-data"}}).then(response => {
        console.log('submit signature' + response.status)
        return response.data
    }).catch(error => {
        console.log(error)
        return error.status
    })
}

export function submitApplicant(form, edu, work, relation) {

    return axios.post('http://localhost:8080/applicant', {
        ...form,
        edu,
        work,
        relation,
    }).then(response => {
        console.log('submit app' + response.status)
        return response.status
    }).catch(error => {
        console.log(error)
        return error.status
    })
}