import React, { Component } from 'react';
import { SERVER_URL } from '../constants.js';
import ReactTable from "react-table";
import 'react-table/react-table.css';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';


class Carlist extends Component {
    
    constructor(props) {
        super(props);
        this.state = { cars: [] };
    }

    componentDidMount() {
        this.fetchCars();
    }

    fetchCars = () => {
        fetch(SERVER_URL + 'api/cars')
        .then((response) => response.json())
        .then((responseData) => {
            this.setState({
                cars: responseData._embedded.cars,
            });
        })
        .catch(err => console.error(err));
    }

    onDelClick = (link) => {
        fetch(link, {method: 'DELETE'})
        .then(res => {
            toast.success("Car deleted", {
                position: toast.POSITION.BOTTOM_LEFT
            });
            this.fetchCars();
        })
        .catch(err => {
            toast.success("Error while deleting", {
                position: toast.POSITION.BOTTOM_LEFT
            });
            console.error(err);
        })
    }

    confirmDelete = (link) => {
        confirmAlert({
            message: 'Are you sure you want to delete?',
            buttons: [
                {
                    label: 'Yes',
                    onClick: () => this.onDelClick(link)
                },
                {
                    label: 'No'
                }
            ]
        })
    }

    render() {
        const columns = [{
            Header: 'Brand',
            accessor: 'brand'
        }, {
            Header: 'Model',
            accessor: 'model'
        }, {
            Header: 'Colour',
            accessor: 'color'
        }, {
            Header: 'Year',
            accessor: 'year'
        }, {
            Header: 'Price £',
            accessor: 'price'
        }, {
            id: 'delbutton',
            sortable: false,
            width: 100,
            accessor: '_links.self.href',
            Cell: ({value}) => (<button onClick={()=>{this.confirmDelete(value)}}>Delete</button>)
        }]
        return (
            <div className="App">
                <ReactTable data={this.state.cars} columns={columns} filterable={true} />
                <ToastContainer autoClose={1500} />
            </div>
        );
    }
}

export default Carlist;