class TokenStorage {
    static store(token) {
        localStorage.setItem('token', JSON.stringify(token));
    }

    static get() {
        let token = localStorage.getItem('token');
        return token ? JSON.parse(token) : null;
    }

    static clear() {
        localStorage.removeItem('token')
    }
}

class AxiosUtils {
    static composeRequestConfig() {
        let token = TokenStorage.get();
        let authorizationHeader = token ? token.token : null;
        let config = {
            headers: {
                'Authorization': authorizationHeader
            }
        };
        return config;
    }
}

const Tasks = Vue.component('Tasks', {
    data: () => {
        return {
            tasks: [],
            taskDescription: '',
        }
    },
    template: `
        <div>
            <div>
                <input type="text" v-model="taskDescription">
                <button v-on:click="createTask" 
                    class="btn btn-primary" 
                    :disabled="isAddDisabled">Add Task</button>        
            </div>
            <ul class="list-group">
              <li class="list-group-item" v-for="task in tasks">             
                <input type="checkbox" v-model="task.checked" v-on:click="toggleTask(task)">
                <span>{{ task.description }}</span>
                <br>
                <small>{{ task.timestampUpdated }}</small>
                <span class="float-right" v-on:click="deleteTask(task)">x</span>
              </li>
            </ul>
        </div>
        `,
    methods: {
        createTask: function() {
            let task = {
                description: this.taskDescription
            };
            axios.post('/api/tasks', task, AxiosUtils.composeRequestConfig()).then((res) => {
                this.taskDescription = '';
                this.tasks.push(res.data);
            });
        },
        deleteTask: function(task) {
            console.log(task);
            axios.delete(`/api/tasks/${task.id}`, AxiosUtils.composeRequestConfig()).then((res) => {
                let i = this.tasks.findIndex(item => item.id === task.id);
                this.tasks.splice(i, 1);
            });
        },
        toggleTask: function(task) {
            let action = {
                action: task.checked ? 'UNCHECK' : 'CHECK'
            };
            axios.post(`/api/tasks/${task.id}/action`, action, AxiosUtils.composeRequestConfig()).then((res) => {
                let i = this.tasks.findIndex(item => item.id === task.id);
                this.tasks.splice(i, 1, res.data);
            });
        }

    },
    mounted: function() {
        axios.get('/api/tasks', AxiosUtils.composeRequestConfig()).then((res) => {
            this.tasks = res.data
        });
    },
    computed: {
        isAddDisabled: function() {
            return !this.taskDescription;
        }
    }
});

const LoginForm = Vue.component('LoginForm', {
    data: () => {
        return {
            username: '',
            password: '',
            errorLogin: false
        }
    },
    template: `
        <div class="card">
            <div class="card-body">
                <div class="alert alert-danger" role="alert" v-if="errorLogin">
                  Login error
                </div>            
                <div class="form-horizontal">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" name="username" 
                            class="form-control" 
                            id="username" 
                            placeholder="username" 
                            required="" 
                            autofocus=""
                            v-model="username">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" name="password" 
                            class="form-control" 
                            id="password" 
                            placeholder="password" 
                            required=""
                            v-model="password">
                    </div>                                      
                    <div>
                        <button v-on:click="login" 
                            :disabled="isLoginDisabled" 
                            class="btn btn-success">Login</button>
                     </div>                  
                </div>
            </div>
        </div>`,
    methods: {
        login: function() {
            this.errorLogin = false;
            axios.post('/auth/login', {
                username: this.username,
                password: this.password
            }).then((res) => {
                TokenStorage.store(res.data);
                this.$emit('loggedIn')
            }).catch((err) => {
                console.error(err);
                this.errorLogin = true;
            })
        }
    },
    computed: {
        isLoginDisabled() {
            return !this.username || !this.password;
        }
    }
});

// Define a new component called button-counter
const App = Vue.component('App', {
    template: `
        <div class="container">
            <Tasks v-if="isLoggedIn"/>
            <LoginForm v-else="isLoggedIn" v-on:loggedIn="onLoggedIn"/>
        </div>`,
    data: () => {
        return {
            isLoggedIn: false,
        }
    },
    mounted: function() {
        this.isLoggedIn = !!TokenStorage.get();
    },
    methods: {
        onLoggedIn: function() {
            this.isLoggedIn = true;
        }
    },
    computed: {
    }

});

new Vue({
    el: '#app',
    template: '<App/>',
    components: {
        App: App
    }
});