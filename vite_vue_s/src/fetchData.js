import axios from 'axios';

export function getDepartments(search) {
    // 发起获取数据的请求，替换以下 URL 为你的接口地址
    return axios.get('http://localhost:8080/getDepartments?name=' + search).then(response => {
        console.log(response.data);
        return response.data.Departments;
    });
}

export function getUserData() {
    return axios.get('http://localhost:8080/getUsers').then(response => {
        if (response.status === 200) {
            return response.data;
        } else {
            throw new Error('更新用户失败');
        }
    });
}

async function uploadPortrait(portraitFile) {
    const formData = new FormData()
    formData.append('file', portraitFile)
    const response = await axios.post('/upload', formData)
    return response.data.url
}

//down load docx file from server
export function downloadFile(id,name) {
    axios({
        method: 'get',
        url: `http://localhost:8080/applicant/download/${id}`,
        responseType: 'blob',
        headers: {
            'Content-Type': 'application/octet-stream',
        },
    }).then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', name+'_员工应聘登记表.docx')
        document.body.appendChild(link)
        link.click()
    })
}
